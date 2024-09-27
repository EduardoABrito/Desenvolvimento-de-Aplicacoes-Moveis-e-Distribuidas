## Índice

- [Visão Geral](#visão-geral)
- [Fluxo](#fluxo)
- [Componentes Principais](#componentes-principais)
- [Instruções de Execução](#instruções-de-execução)
- [Variáveis de Ambiente](#variáveis-de-Ambiente)
- [Diferenças entre Fila e Pub/Sub no RabbitMQ](#diferenças-entre-fila-e-pubsub-no-rabbitmq)

---

## Visão Geral

O sistema de sorveteria é projetado para fornecer uma experiência interativa e eficiente tanto para os clientes quanto para a administração da sorveteria. Utilizando RabbitMQ como um middleware de mensagens, o sistema é dividido em quatro componentes principais que trabalham juntos para gerenciar pedidos e manter os clientes informados sobre novidades.

## Fluxo

1. Sorveteria (Ice Cream Shop)
   A Sorveteria é o núcleo do sistema, responsável por gerenciar as operações do dia a dia. Ela publica atualizações sobre novos sabores e gerencia o recebimento de pedidos dos clientes. Com o uso do RabbitMQ, a sorveteria envia mensagens para os clientes que demonstram interesse em novidades, garantindo que todos sejam informados sobre novos produtos assim que estão disponíveis. Além disso, ela processa os pedidos de sorvete, garantindo que os clientes recebam suas solicitações de forma rápida e organizada.

2. Produtor (Producer)
   O Produtor é responsável por gerar pedidos de sorvete e enviá-los para a fila do RabbitMQ. Ele simula a criação de pedidos, atribuindo informações como tipo de sabor e quantidade, e garante que essas informações sejam enviadas para a sorveteria de maneira eficiente. Este componente é fundamental para o fluxo de pedidos, permitindo que a sorveteria receba as solicitações dos clientes de forma estruturada e organizada.

3. Produtor Interessado (Producer Interested)
   O Produtor Interessado atua como um cliente que se inscreve para receber notificações sobre novos sabores de sorvete. Ele escuta as mensagens publicadas no exchange do RabbitMQ, garantindo que os clientes sejam notificados sobre as novidades assim que são anunciadas pela sorveteria. Esse componente é essencial para manter os clientes engajados e informados sobre as opções mais recentes disponíveis, promovendo uma comunicação ativa entre a sorveteria e os clientes.

4. RabbitMQ
   O RabbitMQ é o sistema de mensagens que orquestra a comunicação entre todos os componentes. Ele gerencia as filas e exchanges, garantindo que as mensagens sobre novos sabores sejam entregues a todos os clientes interessados e que os pedidos sejam processados corretamente na ordem em que foram recebidos. RabbitMQ proporciona uma infraestrutura robusta e eficiente, permitindo que os diferentes componentes do sistema se comuniquem de maneira fluida e eficaz.

## Componentes Principais

- **Sorveteria (Ice Cream Shop)**:

  - Publica atualizações sobre novos sabores e gerencia o recebimento de pedidos dos clientes.
  - Utiliza RabbitMQ para enviar mensagens aos clientes interessados em novidades e processar pedidos de sorvete.

- **Produtor (Producer)**:

  - Envia pedidos de sorvete para a fila.
  - Simula a criação de pedidos e os envia para a sorveteria.

- **Produtor Interessado (Producer Interested)**:

  - Inscreve-se para receber atualizações sobre novos sabores de sorvete da sorveteria.
  - Escuta as mensagens publicadas no exchange e notifica os clientes sobre as novidades, garantindo que todos fiquem informados ao mesmo tempo.

- **RabbitMQ**:
  - Gerencia as filas e exchanges, garantindo que as mensagens sobre novos sabores sejam entregues a todos os clientes interessados e que os pedidos sejam processados corretamente na ordem.
  - Proporciona um sistema de mensagens eficiente para a comunicação entre os diferentes componentes.

## Instruções de execução

Para garantir que o sistema funcione corretamente, siga a sequência de inicialização abaixo:

1. Inicializar os Clientes
   Após iniciar, os clientes realizarão pedidos para a sorveteria.

```bash
   yarn start:producer
   # ou
   npm run start:producer
```

2. Inicializar os Clientes Interessados em Novos Sabores
   Após a inicialização, esses clientes ficarão ouvindo atualizações da sorveteria sobre novos sabores disponíveis.

```bash
   yarn start:producer-interested
   # ou
   npm run start:producer-interested
```

3. Iniciar a Sorveteria
   No início, a sorveteria anunciará os novos sabores. Após isso, ela começará a processar os pedidos recebidos.

```bash
   yarn start:producer-interested
   # ou
   npm run start:producer-interested
```

## Variáveis de Ambiente

Certifique-se de que as variáveis de ambiente estão configuradas corretamente no arquivo de configuração localizado no diretório raiz do projeto.

```env
RABBITMQ_CONNECT_URI=amqp://localhost
```

## Diferenças entre Fila e Pub/Sub no RabbitMQ

Este documento descreve as principais diferenças entre as abordagens de fila tradicional e Pub/Sub (Publicar/Assinar) no RabbitMQ, explicando seus conceitos e principais aplicações.

---

### Fila no RabbitMQ

O RabbitMQ oferece suporte ao modelo de fila tradicional, onde as mensagens são enfileiradas e consumidas por um ou mais consumidores. Neste modelo, uma vez que uma mensagem é retirada da fila por um consumidor, ela não é mais acessível por outros consumidores, garantindo que cada mensagem seja processada uma única vez.

#### Características principais:

- **Entrega exclusiva:** Cada mensagem da fila é enviada para apenas um consumidor.
- **Distribuição de carga:** Vários consumidores podem estar conectados à mesma fila, permitindo que a carga de trabalho seja distribuída entre eles. Isso é útil em cenários de processamento paralelo de tarefas.
- **Persistência:** As mensagens podem ser persistidas em disco, garantindo sua segurança em caso de falhas no sistema.
- **Cenário de uso típico:** Fila de tarefas, onde se deseja que cada tarefa (mensagem) seja processada por apenas um consumidor.

---

### Pub/Sub no RabbitMQ

O RabbitMQ também suporta o padrão Pub/Sub, utilizando o conceito de _exchanges_ e _bindings_. Neste modelo, as mensagens são publicadas em um tópico (via _exchange_), e todos os consumidores que estiverem inscritos (via _bindings_ em filas) recebem essas mensagens. Ao contrário do modelo de fila tradicional, no Pub/Sub, uma mensagem é distribuída para vários consumidores.

#### Características principais:

- **Distribuição ampla:** Uma mensagem publicada em um _exchange_ é enviada a todos os consumidores que estiverem inscritos em filas ligadas a esse _exchange_.
- **Assinatura múltipla:** Diferentes consumidores podem se inscrever em uma mesma _exchange_ e receber as mesmas mensagens.
- **Desacoplamento:** Os publicadores e consumidores não precisam conhecer uns aos outros, permitindo uma arquitetura mais flexível e modular.
- **Cenário de uso típico:** Notificações ou broadcasts, onde uma mesma mensagem precisa ser enviada a vários consumidores simultaneamente.

---

### Comparação

| Aspecto               | Fila no RabbitMQ                                              | Pub/Sub no RabbitMQ                                           |
| --------------------- | ------------------------------------------------------------- | ------------------------------------------------------------- |
| **Padrão de entrega** | Mensagens entregues a um único consumidor                     | Mensagens entregues a todos os assinantes                     |
| **Persistência**      | As mensagens podem ser persistidas                            | Depende da configuração do _exchange_                         |
| **Escalabilidade**    | Ideal para distribuir processamento entre vários consumidores | Ideal para distribuir informações para múltiplos consumidores |

---

### Considerações Finais

O RabbitMQ oferece suporte tanto ao modelo de fila tradicional quanto ao padrão Pub/Sub, atendendo diferentes necessidades de processamento e distribuição de mensagens. O modelo de fila é ideal para garantir que cada mensagem seja processada por apenas um consumidor, enquanto o Pub/Sub é excelente para distribuir uma mesma mensagem a vários consumidores.
