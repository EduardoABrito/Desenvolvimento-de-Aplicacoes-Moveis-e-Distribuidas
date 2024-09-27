import { IceCreamShop } from "./ice-cream-shop";

require("dotenv").config();
async function start() {
  const iceCreamShop = new IceCreamShop("ice-cream-shop", 1);

  await iceCreamShop.initial();

  await iceCreamShop.publishNewFlavor();
}

start();
