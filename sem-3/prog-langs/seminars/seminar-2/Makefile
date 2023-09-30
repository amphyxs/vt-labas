all: task-2.asm
	nasm -f elf64 -o task-2.o task-2.asm
	ld -o task-2 task-2.o

debug:
	make
	gdb task-2 -q -ex "layout asm" -ex "layout regs"
