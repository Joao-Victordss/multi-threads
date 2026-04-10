# Calculo Distribuido com Sockets

Projeto simples em Java para demonstrar comunicacao em rede com sockets e multithreading.
Compatibilidade sugerida: Java 8 ou superior.

## Estrutura

- `CalculationServer`: abre a porta e aceita varios clientes.
- `ClientHandler`: cada cliente conectado e atendido em uma thread separada.
- `ExpressionEvaluator`: valida e calcula a operacao recebida.
- `CalculationClient`: conecta ao servidor, envia a operacao e mostra o resultado.

## Como compilar

No computador que tiver o Java instalado:

```bash
javac -d out src/com/distributedcalc/*.java
```

## Como executar o servidor

```bash
java -cp out com.distributedcalc.CalculationServer
```

Para usar outra porta:

```bash
java -cp out com.distributedcalc.CalculationServer 6000
```

## Como executar o cliente

No mesmo computador:

```bash
java -cp out com.distributedcalc.CalculationClient 127.0.0.1 5000
```

Em outro computador da mesma rede:

```bash
java -cp out com.distributedcalc.CalculationClient IP_DO_SERVIDOR 5000
```

Exemplo:

```bash
java -cp out com.distributedcalc.CalculationClient 192.168.0.10 5000
```

## Operacoes aceitas

- `2 + 3`
- `10 / 2`
- `7.5 * 4`
- `9 - 1`

Para encerrar o cliente, digite `sair`.

## Fluxo da aplicacao

1. O servidor fica escutando em uma porta.
2. Quando um cliente conecta, o servidor cria uma nova thread para esse cliente.
3. O cliente envia uma operacao em texto.
4. O servidor calcula o resultado e devolve a resposta.
5. Varios clientes podem fazer isso ao mesmo tempo.

## Observacoes para a apresentacao

- O servidor usa `ServerSocket` para ficar aguardando conexoes.
- O cliente usa `Socket` para conectar ao servidor.
- O multithreading acontece com uma `Thread` para cada cliente conectado.
- Cada responsabilidade foi separada em uma classe para deixar o codigo mais organizado e facil de explicar.
