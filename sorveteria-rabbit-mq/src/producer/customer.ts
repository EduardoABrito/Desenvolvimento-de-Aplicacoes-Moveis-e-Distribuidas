import amqp from "amqplib";
import { IceCreamShop } from "../consumer/ice-cream-shop";

export class Customer {
  constructor(name: string) {
    this.name = name;
  }

  protected readonly queue: string = "ice-cream-shop";
  protected readonly name: string;
  protected channel: any;

  private async startRabbitMq() {
    try {
      const connection = await amqp.connect(process.env.RABBITMQ_CONNECT_URI!);
      this.channel = await connection.createChannel();

      await this.channel.assertQueue(this.queue, {
        durable: true,
      });
    } catch (error) {
      console.error("Erro ao iniciar rabbit mq:", error);
    }
  }

  async sendMessage(message: string) {
    try {
      message = `${this.name} - ${message}`;

      await new Promise((resolve) => {
        setTimeout(() => {
          resolve("");
          this.channel.sendToQueue(this.queue, Buffer.from(message));
        }, 100);
      });

      console.log(`[x] ${message}`);
    } catch (error) {
      console.log(`erro ao enviar mensagem:`, error);
    }
  }

  private generateOrder() {
    const flavorsIceCream = IceCreamShop.flavorsIceCream;

    const randomFlavor =
      flavorsIceCream[Math.floor(Math.random() * flavorsIceCream.length)];

    const randomQuantity = Math.floor(Math.random() * 5) + 1;

    return {
      flavor: randomFlavor,
      quantity: randomQuantity,
    };
  }

  async initial() {
    await this.startRabbitMq();
  }

  async createOrders(quantity: number) {
    for (let i = 0; i < quantity; i++) {
      const { quantity, flavor } = this.generateOrder();

      await this.sendMessage(`${quantity} ${flavor}`);
    }
  }
}
