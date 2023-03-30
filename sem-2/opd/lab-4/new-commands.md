CALL 0xXXX (DXXX):
IF ok
AF fetch arg's address
OF get operand in DR
EX DR -> BR, IP -> DR, BR -> IP, SP + ~0 -> SP AR, DR -> MEM(AR), GOTO INT

RET (0A00)
IF -
AF -
OF -
EX SP -> AR, MEM(AR) -> DR, DR -> IP, SP + 1 -> SP

POP (0800)
IF -
AF -
OF -
EX SP -> AR, MEM(AR) -> DR, DR -> AC, SP + 1 -> SP

PUSH (0C00)
IF -
AF -
OF -
EX AC -> DR, SP - 1 -> AR SP, DR -> MEM(AR)