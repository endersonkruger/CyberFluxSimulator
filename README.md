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
│   │   ├── Cliente.java             # Classe abstrata para clientes
│   │   ├── CyberFluxSimulator.java  # Classe principal
│   │   ├── Estudante.java           # Cliente Estudante
│   │   ├── Freelancer.java          # Cliente Freelancer
│   │   ├── Gamer.java               # Cliente Gamer
│   │   ├── GerenciadorRecursos.java # Controle de alocação de recursos
│   │   ├── Logger.java              # Logs do sistema
│── README.md                        # Este arquivo 😉
│── CyberFluxSimulator.iml           # Dependências (Gradle/Maven)
```


## 📌 Tecnologias Utilizadas
🔹 **Java** - Linguagem principal do projeto

🔹 **Threads e Semáforos** - Para controle concorrente



## 📖 Licença
Este projeto está sob a licença MIT. Sinta-se à vontade para contribuir! 💙

---
**CyberFlux Simulator** - Simulação Inteligente para um Cyber Café Futurista! 🚀

