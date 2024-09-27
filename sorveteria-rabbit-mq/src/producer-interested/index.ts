import { CustomerInterested } from "./customer-interested";

require("dotenv").config();

async function start() {
  console.log("Fiquem ligados nos novos sabores! 🍦");
  const customerInterested1 = new CustomerInterested("Joao");
  const customerInterested2 = new CustomerInterested("Julia");
  const customerInterested3 = new CustomerInterested("Marcos");
  const customerInterested4 = new CustomerInterested("Cleiton");

  await customerInterested1.initial();
  await customerInterested2.initial();
  await customerInterested3.initial();
  await customerInterested4.initial();

  await customerInterested1.receiveFlavorUpdates();
  await customerInterested2.receiveFlavorUpdates();
  await customerInterested3.receiveFlavorUpdates();
  await customerInterested4.receiveFlavorUpdates();
}

start();
