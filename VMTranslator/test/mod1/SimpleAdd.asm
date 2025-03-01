// C_PUSH constant 7
@7
D=A
@SP
A=M
M=D
@SP
M=M+1
// C_PUSH constant 8
@8
D=A
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
