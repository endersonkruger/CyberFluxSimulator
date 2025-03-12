# CyberFlux Simulator 🚀

**CyberFlux Simulator** é um simulador multithread de um cyber café futurista, onde clientes disputam recursos limitados como PCs de última geração, headsets de realidade virtual e cadeiras ergonômicas. O objetivo do projeto é gerenciar esses recursos de forma eficiente, evitando **deadlocks** e **starvation**.

## 📌 Funcionalidades
✅ Gerenciamento concorrente de clientes utilizando **threads**.

✅ Controle de acesso aos recursos via **semáforos**.

✅ Simulação de diferentes perfis de clientes:
   - **Gamer 🎮**: Prioriza PC + Headset VR.
   - **Freelancer 💻**: Prioriza PC + Cadeira.
   - **Estudante 📚**: Precisa apenas de um PC.

✅ Estatísticas no final da simulação:
   - Número total de clientes atendidos.
   - Tempo médio de espera por recurso.
   - Taxa de utilização de cada recurso.

✅ Estratégias para evitar **deadlock** e **starvation**.

## 🏗 Estrutura do Projeto
```
CyberFluxSimulator/
│── src/
│   ├── main/
│   │   ├── CyberFluxSimulator.java      # Classe principal
│   │   ├── models/
│   │   │   ├── Cliente.java             # Classe abstrata para clientes
│   │   │   ├── Gamer.java               # Cliente Gamer
│   │   │   ├── Freelancer.java          # Cliente Freelancer
│   │   │   ├── Estudante.java           # Cliente Estudante
│   │   ├── resources/
│   │   │   ├── Recurso.java             # Interface para recursos
│   │   │   ├── PC.java                  # Classe para PC
│   │   │   ├── Headset.java             # Classe para Headset VR
│   │   │   ├── Cadeira.java             # Classe para Cadeira ergonômica
│   │   ├── management/
│   │   │   ├── GerenciadorRecursos.java # Controle de alocação de recursos
│   │   │   ├── Estatisticas.java        # Coleta de estatísticas
│   │   ├── utils/
│   │   │   ├── Logger.java              # Logs do sistema
│   │   │   ├── RandomUtils.java         # Métodos auxiliares
│── test/
│   ├── CyberFluxSimulatorTest.java      # Testes unitários
│── README.md                            # Este arquivo 😉
│── build.gradle / pom.xml               # Dependências (Gradle/Maven)
```

## 🚀 Como Rodar o Projeto
### Usando Gradle
1. Clone o repositório:
   ```sh
   git clone https://github.com/seu-usuario/CyberFluxSimulator.git
   cd CyberFluxSimulator
   ```
2. Compile e execute:
   ```sh
   ./gradlew run
   ```
3. Para rodar os testes:
   ```sh
   ./gradlew test
   ```

### Usando Maven
1. Clone o repositório:
   ```sh
   git clone https://github.com/seu-usuario/CyberFluxSimulator.git
   cd CyberFluxSimulator
   ```
2. Compile e execute:
   ```sh
   mvn compile exec:java
   ```
3. Para rodar os testes:
   ```sh
   mvn test
   ```

## 📌 Tecnologias Utilizadas
🔹 **Java** - Linguagem principal do projeto

🔹 **Threads e Semáforos** - Para controle concorrente

🔹 **Gradle/Maven** - Gerenciamento de dependências e build

🔹 **JUnit** - Para testes automatizados

## 📖 Licença
Este projeto está sob a licença MIT. Sinta-se à vontade para contribuir! 💙

---
**CyberFlux Simulator** - Simulação Inteligente para um Cyber Café Futurista! 🚀
