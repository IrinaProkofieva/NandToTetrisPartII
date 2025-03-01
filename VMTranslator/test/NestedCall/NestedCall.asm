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
// function Sys.init 0
// label Sys.init
(Sys.init)
@5
M=0
// C_PUSH constant 4000
@4000
D=A
@SP
A=M
M=D
@SP
M=M+1
// C_POP pointer 0
@0
D=A
@THIS
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
// C_PUSH constant 5000
@5000
D=A
@SP
A=M
M=D
@SP
M=M+1
// C_POP pointer 1
@1
D=A
@THIS
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
// call Sys.main 0
// push return address
@Sys.main$ret.1
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
// goto Sys.main
@Sys.main
0;JMP
// label Sys.main$ret.1
(Sys.main$ret.1)
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
// label LOOP
(LOOP)
// goto LOOP
@LOOP
0;JMP
// function Sys.main 5
// label Sys.main
(Sys.main)
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
// C_PUSH constant 4001
@4001
D=A
@SP
A=M
M=D
@SP
M=M+1
// C_POP pointer 0
@0
D=A
@THIS
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
// C_PUSH constant 5001
@5001
D=A
@SP
A=M
M=D
@SP
M=M+1
// C_POP pointer 1
@1
D=A
@THIS
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
// C_PUSH constant 200
@200
D=A
@SP
A=M
M=D
@SP
M=M+1
// C_POP local 1
@1
D=A
@LCL
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
// C_PUSH constant 40
@40
D=A
@SP
A=M
M=D
@SP
M=M+1
// C_POP local 2
@2
D=A
@LCL
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
// C_PUSH constant 6
@6
D=A
@SP
A=M
M=D
@SP
M=M+1
// C_POP local 3
@3
D=A
@LCL
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
// C_PUSH constant 123
@123
D=A
@SP
A=M
M=D
@SP
M=M+1
// call Sys.add12 1
// push return address
@Sys.add12$ret.2
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
// goto Sys.add12
@Sys.add12
0;JMP
// label Sys.add12$ret.2
(Sys.add12$ret.2)
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
// C_PUSH local 2
@2
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
// C_PUSH local 3
@3
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
// C_PUSH local 4
@4
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
// function Sys.add12 0
// label Sys.add12
(Sys.add12)
@5
M=0
// C_PUSH constant 4002
@4002
D=A
@SP
A=M
M=D
@SP
M=M+1
// C_POP pointer 0
@0
D=A
@THIS
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
// C_PUSH constant 5002
@5002
D=A
@SP
A=M
M=D
@SP
M=M+1
// C_POP pointer 1
@1
D=A
@THIS
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
// C_PUSH constant 12
@12
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
