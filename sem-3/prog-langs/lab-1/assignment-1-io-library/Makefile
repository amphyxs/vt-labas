all: lib.inc debug.asm
	nasm -f elf64 -o lib.o lib.inc
	nasm -f elf64 -o debug.o debug.asm
	ld -o lib lib.o
	ld -o debug debug.o lib.o

debug:
	make
	gdb debug -q -ex "layout asm" -ex "layout regs"

clean:
	rm lib.o lib
	rm debug.o debug

.PHONY: clean, debug
