import { Customer } from "./customer";
require("dotenv").config();

async function start() {
  console.log("Anotando Pedidos 💸");
  const customer1 = new Customer("Joao");
  const customer2 = new Customer("Julia");
  const customer3 = new Customer("Marcos");
  const customer4 = new Customer("Cleiton");

  await customer1.initial();
  await customer2.initial();
  await customer3.initial();
  await customer4.initial();

  await customer1.createOrders(4);
  await customer2.createOrders(6);
  await customer3.createOrders(8);
  await customer4.createOrders(9);
}

start();
