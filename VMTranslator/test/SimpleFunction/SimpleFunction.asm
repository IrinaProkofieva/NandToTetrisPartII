@256
D=A
@SP
M=D
// function SimpleFunction.test 2
// label SimpleFunction.test
(SimpleFunction.test)
@5
M=0
// C_PUSH temp 0
@0
D=A
@5
D=D+A
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
// C_PUSH temp 0
@0
D=A
@5
D=D+A
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
// C_PUSH local 0
@0
D=A
@LCL
A=M
D=D+A
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
// C_PUSH local 1
@1
D=A
@LCL
A=M
D=D+A
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
// add
// C_POP temp 0
@0
D=A
@5
D=D+A
@7
M=D
@SP
M=M-1
A=M
D=M
@7
A=M
M=D
// C_POP temp 1
@1
D=A
@5
D=D+A
@7
M=D
@SP
M=M-1
A=M
D=M
@7
A=M
M=D
@5
D=M
@6
M=D+M
// C_PUSH temp 1
@1
D=A
@5
D=D+A
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
// not
// C_POP temp 0
@0
D=A
@5
D=D+A
@7
M=D
@SP
M=M-1
A=M
D=M
@7
A=M
M=D
@5
M=!M
// C_PUSH temp 0
@0
D=A
@5
D=D+A
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
// C_PUSH argument 0
@0
D=A
@ARG
A=M
D=D+A
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
// add
// C_POP temp 0
@0
D=A
@5
D=D+A
@7
M=D
@SP
M=M-1
A=M
D=M
@7
A=M
M=D
// C_POP temp 1
@1
D=A
@5
D=D+A
@7
M=D
@SP
M=M-1
A=M
D=M
@7
A=M
M=D
@5
D=M
@6
M=D+M
// C_PUSH temp 1
@1
D=A
@5
D=D+A
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
// C_PUSH argument 1
@1
D=A
@ARG
A=M
D=D+A
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
// sub
// C_POP temp 0
@0
D=A
@5
D=D+A
@7
M=D
@SP
M=M-1
A=M
D=M
@7
A=M
M=D
// C_POP temp 1
@1
D=A
@5
D=D+A
@7
M=D
@SP
M=M-1
A=M
D=M
@7
A=M
M=D
@5
D=M
@6
M=M-D
// C_PUSH temp 1
@1
D=A
@5
D=D+A
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
// return
// endFrame = LCL
@LCL
D=M
// retAddr = *(endFrame - 5)
@5
M=D
D=D-A
A=D
D=M
@6
M=D
// *ARG = pop()
// C_POP argument 0
@0
D=A
@ARG
A=M
D=D+A
@7
M=D
@SP
M=M-1
A=M
D=M
@7
A=M
M=D
// SP = ARG + 1
@ARG
D=M+1
@SP
M=D
// THAT = *(endFrame - 1)
@5
D=M
D=D-1
A=D
D=M
@THAT
M=D
// THIS = *(endFrame - 2)
@5
D=M
D=D-1
D=D-1
A=D
D=M
@THIS
M=D
// ARG = *(endFrame - 3)
@5
D=M
D=D-1
D=D-1
D=D-1
A=D
D=M
@ARG
M=D
// LCL = *(endFrame - 4)
@5
D=M
D=D-1
D=D-1
D=D-1
D=D-1
A=D
D=M
@LCL
M=D
// goto retAddr
@6
A=M
0;JMP
