# Expression-Parser

Expression Parser supports division, addition, subtraction, multiplication, minus operator.

Parser runs in linear time using the recursive descent method. Application also has special exceptions special exceptions responsible for overflows, division by zero, and expression legibility. 

```mermaid
flowchart LR
  subgraph TOP
    direction TB
    subgraph LexerWorker
        direction RL
        CharSource-->Lexer
    end
    subgraph B2
        direction BT
        i2 -->f2
    end
  end
  A --> TOP --> B
```
