import { sinTaylor } from './sin-taylor';

describe('sinTaylor', () => {
  const testCases = [
    0.0,
    Math.PI / 6,
    Math.PI / 2,
    Math.PI,
    (3 * Math.PI) / 2,
    2 * Math.PI,
  ];

  testCases.forEach((x) => {
    const expected = Math.sin(x);

    it(`should return ${expected} for x = ${x}`, () => {
      const result = sinTaylor(x);
      expect(result).toBeCloseTo(expected, 5);
    });
  });

  it('should handle large values of x', () => {
    const x = 10 * Math.PI;
    const result = sinTaylor(x);
    expect(result).toBeCloseTo(Math.sin(x), 5);
  });

  it('should handle negative values of x', () => {
    const x = -Math.PI / 4;
    const result = sinTaylor(x);
    expect(result).toBeCloseTo(Math.sin(x), 5);
  });
});
