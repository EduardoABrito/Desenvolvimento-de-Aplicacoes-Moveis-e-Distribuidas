import { Customer } from "../producer/customer";

export class CustomerInterested extends Customer {
  constructor(name: string) {
    super(name);
  }

  private exchange = "ice_cream_flavors";

  async receiveFlavorUpdates() {
    try {
      await this.channel.assertExchange(this.exchange, "fanout", {
        durable: false,
      });

      const { queue } = await this.channel.assertQueue("", { exclusive: true });

      this.channel.bindQueue(queue, this.exchange, "");

      console.log(
        `[x] ${this.name} - Aguardando novas atualizações de sabores.`
      );

      this.channel.consume(
        queue,
        (msg: any) => {
          console.log(`😁 ${this.name} recebeu: ${msg.content.toString()}`);
        },
        {
          noAck: true,
        }
      );
    } catch (error) {
      console.error("Erro ao receber mensagens:", error);
    }
  }
}
