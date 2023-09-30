%include "lib.inc"

global _start

section .data
str: dw 'ashdb asdhabs dahb', 0
buf: times 50 dw 0


section .text
_start:
    mov rdi, str
    mov rsi, buf
    mov rdx, 50
    call string_copy
    xor rdi, rdi
    mov rax, 60
    syscall
