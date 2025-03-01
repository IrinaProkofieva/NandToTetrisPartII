@256
D=A
@SP
M=D
// call Sys.init 0
// push return address
@Sys.init$ret.0
D=A
@5
M=D
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
// push @LCL
@LCL
D=M
@5
M=D
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
// push @ARG
@ARG
D=M
@5
M=D
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
// push @THIS
@THIS
D=M
@5
M=D
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
// push @THAT
@THAT
D=M
@5
M=D
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
// ARG = SP - 5 - nArgs
@5
D=A
@0
D=D+A
@SP
D=M-D
@ARG
M=D
// LCL = SP
@SP
D=M
@LCL
M=D
// goto Sys.init
@Sys.init
0;JMP
// label Sys.init$ret.0
(Sys.init$ret.0)
// function Main.fibonacci 0
// label Main.fibonacci
(Main.fibonacci)
@5
M=0
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
// C_PUSH constant 2
@2
D=A
@SP
A=M
M=D
@SP
M=M+1
// lt
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
D=M-D
@TRUE0
D;JLT
D=0
@END0
0;JMP
(TRUE0)
D=-1
(END0)
@5
M=D
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
// if-goto N_LT_2
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
D=M
@N_LT_2
D+1;JEQ
// goto N_GE_2
@N_GE_2
0;JMP
// label N_LT_2
(N_LT_2)
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
// label N_GE_2
(N_GE_2)
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
// C_PUSH constant 2
@2
D=A
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
// call Main.fibonacci 1
// push return address
@Main.fibonacci$ret.1
D=A
@5
M=D
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
// push @LCL
@LCL
D=M
@5
M=D
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
// push @ARG
@ARG
D=M
@5
M=D
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
// push @THIS
@THIS
D=M
@5
M=D
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
// push @THAT
@THAT
D=M
@5
M=D
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
// ARG = SP - 5 - nArgs
@5
D=A
@1
D=D+A
@SP
D=M-D
@ARG
M=D
// LCL = SP
@SP
D=M
@LCL
M=D
// goto Main.fibonacci
@Main.fibonacci
0;JMP
// label Main.fibonacci$ret.1
(Main.fibonacci$ret.1)
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
// C_PUSH constant 1
@1
D=A
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
// call Main.fibonacci 1
// push return address
@Main.fibonacci$ret.2
D=A
@5
M=D
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
// push @LCL
@LCL
D=M
@5
M=D
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
// push @ARG
@ARG
D=M
@5
M=D
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
// push @THIS
@THIS
D=M
@5
M=D
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
// push @THAT
@THAT
D=M
@5
M=D
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
// ARG = SP - 5 - nArgs
@5
D=A
@1
D=D+A
@SP
D=M-D
@ARG
M=D
// LCL = SP
@SP
D=M
@LCL
M=D
// goto Main.fibonacci
@Main.fibonacci
0;JMP
// label Main.fibonacci$ret.2
(Main.fibonacci$ret.2)
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
// function Sys.init 0
// label Sys.init
(Sys.init)
@5
M=0
// C_PUSH constant 4
@4
D=A
@SP
A=M
M=D
@SP
M=M+1
// call Main.fibonacci 1
// push return address
@Main.fibonacci$ret.3
D=A
@5
M=D
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
// push @LCL
@LCL
D=M
@5
M=D
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
// push @ARG
@ARG
D=M
@5
M=D
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
// push @THIS
@THIS
D=M
@5
M=D
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
// push @THAT
@THAT
D=M
@5
M=D
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
// ARG = SP - 5 - nArgs
@5
D=A
@1
D=D+A
@SP
D=M-D
@ARG
M=D
// LCL = SP
@SP
D=M
@LCL
M=D
// goto Main.fibonacci
@Main.fibonacci
0;JMP
// label Main.fibonacci$ret.3
(Main.fibonacci$ret.3)
// label END
(END)
// goto END
@END
0;JMP
