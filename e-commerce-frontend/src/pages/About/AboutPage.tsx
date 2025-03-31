import { Layout } from "@/components/layout/Layout";

export const AboutPage = () => {
  return (
    <Layout>
      <div className="max-w-2xl mx-auto text-center">
        <h1 className="text-3xl font-bold mb-4">About Us</h1>
        <p className="text-stone-600">
          Marcus Bike Store is committed to building high-quality, customizable bikes for every kind of rider. 
          We believe in freedom, speed, and making every ride uniquely yours.
        </p>
      </div>
    </Layout>
  );
};
