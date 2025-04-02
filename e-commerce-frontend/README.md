# 🛍️ E-Commerce Frontend

This is the frontend of a full-featured e-commerce platform built with **React 19**, **Vite**, **TypeScript**, and **TailwindCSS**. It includes functionality for customers and admins, such as product browsing, custom products, order management, and admin dashboards for presets, products and orders.

---

## 📋 Requirements

- **Node.js** >= 18.x
- **npm** >= 9.x
- A running **backend API** (adjust base URL in `.env`)

---

## 🔧 Tech Stack

- ⚛️ **React 19**
- ⚡ **Vite**
- 💨 **TailwindCSS v4**
- 🧪 **TypeScript**
- 🧱 **Ant Design**
- 🔁 **React Query**
- 🔥 **React Hot Toast**
- 🧭 **React Router v7**
- 📦 **Axios**

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/your-user/e-commerce-frontend.git
cd e-commerce-frontend
```

### 2. Install dependencies

```bash
npm install
```

### 3. Environment variables

Create a .env file and add:

```bash
VITE_API_URL=<api-url>
```

### 4. Run development server

```bash
npm run dev
```

## ✨ Features

- 🔐 JWT-based Auth (Login, Register)
- 🛒 Persistent shopping cart (localStorage)
- 🧩 Custom product configuration with dynamic pricing
- 🧑‍💼 Admin dashboard for managing: Products, Categories, Presets, Global orders
- ⚙️ API-based validation & price rules
- 📱 Fully responsive layout
- 📣 Real-time feedback with toast notifications


# OFICIAL DOCUMENTATION -- React + TypeScript + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react/README.md) uses [Babel](https://babeljs.io/) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh

## Expanding the ESLint configuration

If you are developing a production application, we recommend updating the configuration to enable type-aware lint rules:

```js
export default tseslint.config({
  extends: [
    // Remove ...tseslint.configs.recommended and replace with this
    ...tseslint.configs.recommendedTypeChecked,
    // Alternatively, use this for stricter rules
    ...tseslint.configs.strictTypeChecked,
    // Optionally, add this for stylistic rules
    ...tseslint.configs.stylisticTypeChecked,
  ],
  languageOptions: {
    // other options...
    parserOptions: {
      project: ['./tsconfig.node.json', './tsconfig.app.json'],
      tsconfigRootDir: import.meta.dirname,
    },
  },
})
```

You can also install [eslint-plugin-react-x](https://github.com/Rel1cx/eslint-react/tree/main/packages/plugins/eslint-plugin-react-x) and [eslint-plugin-react-dom](https://github.com/Rel1cx/eslint-react/tree/main/packages/plugins/eslint-plugin-react-dom) for React-specific lint rules:

```js
// eslint.config.js
import reactX from 'eslint-plugin-react-x'
import reactDom from 'eslint-plugin-react-dom'

export default tseslint.config({
  plugins: {
    // Add the react-x and react-dom plugins
    'react-x': reactX,
    'react-dom': reactDom,
  },
  rules: {
    // other rules...
    // Enable its recommended typescript rules
    ...reactX.configs['recommended-typescript'].rules,
    ...reactDom.configs.recommended.rules,
  },
})
```
