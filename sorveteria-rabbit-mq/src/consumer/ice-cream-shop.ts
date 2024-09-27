import amqp from "amqplib";

const formatWithTwoDigits = (num: number) => {
  return String(num).padStart(2, "0");
};

export class IceCreamShop {
  constructor(queue: string, countCollaborator: number) {
    this.queue = queue;
    this.countCollaborator = countCollaborator;
  }

  public static readonly flavorsIceCream = [
    "Chocolate",
    "Baunilha",
    "Morango",
    "Menta",
    "Cookies & Creme",
    "Pistache",
    "Manga",
  ];

  private readonly newFlavorsIceCream = [
    "Pistache",
    "Frutas Vermelhas",
    "Chocolate Amargo",
    "Coco",
    "Baunilha com Caramelo Salgado",
  ];

  private queue: string;
  private channel: any;
  private countOrder: number = 1;
  private countCollaborator: number;
  private readonly exchange: string = "ice_cream_flavors";

  private async startRabbitMq() {
    try {
      const connection = await amqp.connect(process.env.RABBITMQ_CONNECT_URI!);
      this.channel = await connection.createChannel();

      await this.channel.assertQueue(this.queue, {
        durable: true,
      });
      console.log(`Sorveteria Aberta | ${this.countCollaborator} Colaborador`);

      this.channel.prefetch(this.countCollaborator);
    } catch (error) {
      console.error("Erro ao iniciar sorveteria:", error);
    }
  }

  async receiveOrders() {
    try {
      console.log(
        `🍨 Sorveteria aguardando pedidos na fila: ${this.queue} 🍦 \n`
      );

      this.channel.consume(
        this.queue,
        async (msg: any) => {
          const order = msg.content.toString();

          await this.processOrder(order, msg);

          this.channel.ack(msg);
        },
        {
          noAck: false,
        }
      );
    } catch (error) {
      console.error("Erro ao receber mensagem:", error);
    }
  }

  async processOrder(order: string, msg: any) {
    console.log(`[${this.countOrder}] Pedido Iniciado: ${order}`);

    await new Promise((resolve) => {
      const randomTime = Math.floor(Math.random() * 1000) + 2000;

      setTimeout(() => {
        resolve("");
      }, randomTime);
    });

    console.log(
      `Pedido Finalizado: ${order}! Agradecemos pela sua preferência. \n`
    );

    this.countOrder++;
  }

  async publishNewFlavor() {
    try {
      const flavor =
        this.newFlavorsIceCream[
          Math.floor(Math.random() * this.newFlavorsIceCream.length)
        ];

      await this.channel.assertExchange(this.exchange, "fanout", {
        durable: false,
      });

      this.channel.publish(
        this.exchange,
        "",
        Buffer.from(`Novo sabor de ${flavor} disponível!`)
      );

      console.log(`Atualização enviada: Novo sabor de ${flavor} disponível!`);
    } catch (error) {
      console.error("Erro ao publicar a mensagem:", error);
    }
  }

  async initial() {
    await this.startRabbitMq();
    await this.receiveOrders();
  }
}
