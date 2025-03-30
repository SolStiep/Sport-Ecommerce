import {
  Product,
  ConditionalPrice,
  Option,
  Part,
  PriceConditionRule,
} from "@/types/product";

/**
 * Enriches the product with the price inside each rule (used to render in form).
 */
export function enrichProductWithRulePrices(product: Product): Product {
  const rulePriceMap = new Map<string, number>();

  product.parts.forEach((part) => {
    part.options.forEach((option) => {
      option.conditionalPrices?.forEach((cp) => {
        if (cp.ruleId && cp.price !== undefined) {
          rulePriceMap.set(cp.ruleId, cp.price);
        }
      });
    });
  });

  const enrichedRules = product.configurator.rules.priceConditionRules.map(
    (rule) => ({
      ...rule,
      price: rulePriceMap.get(rule.id) ?? 0,
    })
  );

  return {
    ...product,
    configurator: {
      ...product.configurator,
      rules: {
        ...product.configurator.rules,
        priceConditionRules: enrichedRules,
      },
    },
  };
}

/**
 * Transforms the product from the form into the shape needed for the backend.
 */
export function transformProductData(formData: Product): Product {
  const priceRules = formData.configurator.rules.priceConditionRules || [];

  const ruleMap = new Map<string, ConditionalPrice[]>();

  for (const rule of priceRules) {
    const { ifOption, requiredOptions, price } = rule;

    const cp: ConditionalPrice = {
      rule: { ifOption, requiredOptions },
      price,
    };

    if (!ruleMap.has(ifOption)) {
      ruleMap.set(ifOption, []);
    }

    ruleMap.get(ifOption)!.push(cp);
  }

  const enrichedParts: Part[] = formData.parts.map((part) => {
    const enrichedOptions: Option[] = part.options.map((opt) => {
      const conditionalPrices = ruleMap.get(opt.id) || [];

      return {
        ...opt,
        conditionalPrices:
          conditionalPrices.length > 0 ? conditionalPrices : undefined,
      };
    });

    return {
      ...part,
      options: enrichedOptions,
    };
  });

  const cleanedRules: PriceConditionRule[] = priceRules.map(
    ({ ifOption, requiredOptions }) => ({
      ifOption,
      requiredOptions,
    })
  );

  return {
    ...formData,
    parts: enrichedParts,
    configurator: {
      ...formData.configurator,
      rules: {
        ...formData.configurator.rules,
        priceConditionRules: cleanedRules,
      },
    },
  };
}

export function getAllOptionsFromProduct(product: Product) {
  return (
    product?.parts.flatMap((part) =>
      part.options.map((opt) => ({
        partId: part.id,
        partName: part.name,
        id: opt.id,
        name: opt.name,
        label: `${part.name} - ${opt.name}`,
        value: opt.id,
      }))
    ) || []
  );
}
