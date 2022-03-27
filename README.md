# Expression-Parser

Expression Parser supports division, addition, subtraction, multiplication, minus operator.

Parser runs in linear time using the recursive descent method. Application also has special exceptions special exceptions responsible for overflows, division by zero, and expression legibility. 

```mermaid
flowchart LR
  subgraph Compiler
    CharSource-->Lexer
    Lexer-->Pipe
    Pipe-->Parser
    Parser-->Translator
    Translator-->CompilerCpp
  end
  Input --> Compiler --> Output
```
