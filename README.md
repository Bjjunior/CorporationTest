# CorporationTest
Recursos Principais:

Transações Financeiras:

Registre transações entre empresas e clientes.
Considere automaticamente as taxas do sistema durante as transações.
Notificações em Tempo Real:

Envie callbacks automáticos para a empresa após a conclusão bem-sucedida de uma transação.
Notifique o cliente por e-mail sobre cada transação por meio de webhooks.
API REST com Spring Boot:

Utiliza o framework Spring Boot para uma configuração rápida e fácil.
Endpoints RESTful bem definidos para acessar, criar e manipular transações.
Tecnologias Utilizadas:

Spring Boot: Framework poderoso para facilitar o desenvolvimento de aplicativos Java.
Jakarta Persistence (JPA): Utilizado para mapeamento objeto-relacional facilitado.
OkHttp: Biblioteca para interações HTTP eficientes, fundamental para callbacks e webhooks.
JUnit e Mockito: Ferramentas essenciais para teste unitário e mock de objetos.
JSON (via Jackson): Para serialização e desserialização de objetos, crucial para comunicações via API.
Como Utilizar:

Configuração do Banco de Dados:

Configure seu banco de dados de acordo com as propriedades definidas no arquivo de configuração.
API REST com Spring Boot:

Utilize os endpoints da API REST para interagir com o sistema:
GET /cliente, /empresa: Recupera a lista de todas as entidade.
POST /cliente, /empresa: Cria uma nova entidade.
GET //cliente{id}, /empresa{id}: Recupera os detalhes de uma entidade específica.
Callbacks e Webhooks:

Configure as URLs dos webhooks para receber callbacks após transações bem-sucedidas.
Testes Unitários com Spring Boot:

Execute os testes unitários, aproveitando a integração fácil com o ecossistema Spring Boot.

Agradecimentos:

O CorporationTest é possivel , graças ao desafio cedido pela empresa Tgid !

Divirta-se transacionando com o CorporationTest! 🚀

