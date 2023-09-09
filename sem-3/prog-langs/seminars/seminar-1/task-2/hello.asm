; hello.asm 
  section .data
  message: db  'hello, world!', 10
  error_message: db  'ERROR: system dead', 10

  section .text
  global _start

  _start:
      mov     rax, 1           ; 'write' syscall number
      mov     rdi, 1           ; stdout descriptor
      mov     rsi, message     ; string address
      mov     rdx, 14          ; string length in bytes
      syscall

      mov     rax, 1           ; 'write' syscall number
      mov     rdi, 2           ; stdout descriptor
      mov     rsi, error_message     ; string address
      mov     rdx, 19         ; string length in bytes
      syscall

      mov     rax, 60          ; 'exit' syscall number
      xor     rdi, rdi
      syscall