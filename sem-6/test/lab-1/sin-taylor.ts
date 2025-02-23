export function sinTaylor(x: number, n_terms = 20): number {
  // Нужно для больших значений x, чтобы приводить их к [-pi; pi], так как sin периодическая
  x = normalizeToPiRange(x);

  let result = 0.0;
  for (let n = 0; n < n_terms; n++) {
    const numerator = (-1) ** n;
    const denominator = factorial(2 * n + 1);
    const term = (numerator / denominator) * x ** (2 * n + 1);
    result += term;
  }
  return result;
}

function factorial(n: number): number {
  if (n === 0 || n === 1) return 1;
  let result = 1;
  for (let i = 2; i <= n; i++) {
    result *= i;
  }
  return result;
}

function normalizeToPiRange(x: number): number {
  const pi = Math.PI;
  x = x % (2 * pi);
  if (x > pi) {
    x -= 2 * pi;
  } else if (x < -pi) {
    x += 2 * pi;
  }
  return x;
}
