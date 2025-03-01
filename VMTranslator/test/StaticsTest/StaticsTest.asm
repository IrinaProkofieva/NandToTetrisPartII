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
// function Class1.set 0
// label Class1.set
(Class1.set)
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
// C_POP static 0
@0
D=A
@Class1.0
D=A
@7
M=D
@SP
M=M-1
A=M
D=M
@7
A=M
M=D
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
// C_POP static 1
@1
D=A
@Class1.1
D=A
@7
M=D
@SP
M=M-1
A=M
D=M
@7
A=M
M=D
// C_PUSH constant 0
@0
D=A
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
// function Class1.get 0
// label Class1.get
(Class1.get)
@5
M=0
// C_PUSH static 0
@Class1.0
D=M
@SP
A=M
M=D
@SP
M=M+1
// C_PUSH static 1
@Class1.1
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
// function Class2.set 0
// label Class2.set
(Class2.set)
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
// C_POP static 0
@0
D=A
@Class2.0
D=A
@7
M=D
@SP
M=M-1
A=M
D=M
@7
A=M
M=D
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
// C_POP static 1
@1
D=A
@Class2.1
D=A
@7
M=D
@SP
M=M-1
A=M
D=M
@7
A=M
M=D
// C_PUSH constant 0
@0
D=A
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
// function Class2.get 0
// label Class2.get
(Class2.get)
@5
M=0
// C_PUSH static 0
@Class2.0
D=M
@SP
A=M
M=D
@SP
M=M+1
// C_PUSH static 1
@Class2.1
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
// function Sys.init 0
// label Sys.init
(Sys.init)
@5
M=0
// C_PUSH constant 6
@6
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
// call Class1.set 2
// push return address
@Class1.set$ret.1
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
@2
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
// goto Class1.set
@Class1.set
0;JMP
// label Class1.set$ret.1
(Class1.set$ret.1)
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
// C_PUSH constant 23
@23
D=A
@SP
A=M
M=D
@SP
M=M+1
// C_PUSH constant 15
@15
D=A
@SP
A=M
M=D
@SP
M=M+1
// call Class2.set 2
// push return address
@Class2.set$ret.2
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
@2
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
// goto Class2.set
@Class2.set
0;JMP
// label Class2.set$ret.2
(Class2.set$ret.2)
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
// call Class1.get 0
// push return address
@Class1.get$ret.3
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
// goto Class1.get
@Class1.get
0;JMP
// label Class1.get$ret.3
(Class1.get$ret.3)
// call Class2.get 0
// push return address
@Class2.get$ret.4
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
// goto Class2.get
@Class2.get
0;JMP
// label Class2.get$ret.4
(Class2.get$ret.4)
// label END
(END)
// goto END
@END
0;JMP
