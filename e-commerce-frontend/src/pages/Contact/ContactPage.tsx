import { Layout } from "@/components/layout/Layout";

export const ContactPage = () => {
  return (
    <Layout>
      <div className="max-w-2xl mx-auto text-center">
        <h1 className="text-3xl font-bold mb-4">Contact</h1>
        <p className="text-gray-600">
          Reach us at <a href="mailto:support@marcusbikes.com" className="text-blue-600 underline">support@marcusbikes.com</a>
          <br />
          Or call us at <span className="font-medium">(123) 456-7890</span>
        </p>
      </div>
    </Layout>
  );
};
