import { selectionSort } from './selection-sort';

describe('selectionSort', () => {
  it('should sort an already sorted array', () => {
    const arr = [1, 2, 3, 4, 5];
    const sortedArr = selectionSort(arr);
    expect(sortedArr).toEqual([1, 2, 3, 4, 5]);
  });

  it('should sort a reverse-sorted array', () => {
    const arr = [5, 4, 3, 2, 1];
    const sortedArr = selectionSort(arr);
    expect(sortedArr).toEqual([1, 2, 3, 4, 5]);
  });

  it('should sort an array with random numbers', () => {
    const arr = [3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5];
    const sortedArr = selectionSort(arr);
    expect(sortedArr).toEqual([1, 1, 2, 3, 3, 4, 5, 5, 5, 6, 9]);
  });

  it('should handle an empty array', () => {
    const arr: number[] = [];
    const sortedArr = selectionSort(arr);
    expect(sortedArr).toEqual([]);
  });

  it('should handle an array with a single element', () => {
    const arr = [42];
    const sortedArr = selectionSort(arr);
    expect(sortedArr).toEqual([42]);
  });

  it('should handle an array with negative numbers', () => {
    const arr = [-3, -1, -4, -1, -5, -9, -2, -6, -5, -3, -5];
    const sortedArr = selectionSort(arr);
    expect(sortedArr).toEqual([-9, -6, -5, -5, -5, -4, -3, -3, -2, -1, -1]);
  });

  it('should handle an array with mixed positive and negative numbers', () => {
    const arr = [-3, 1, -4, 0, 5, -9, 2, 6, -5, 3, 5];
    const sortedArr = selectionSort(arr);
    expect(sortedArr).toEqual([-9, -5, -4, -3, 0, 1, 2, 3, 5, 5, 6]);
  });

  it('should handle an array with floats', () => {
    const arr = [-3.1, 1.5, 1.6, 0, 5.5, -9.1, 2.345343, 6.0000001, 6.0, 3, 5];
    const sortedArr = selectionSort(arr);
    expect(sortedArr).toEqual([
      -9.1, -3.1, 0, 1.5, 1.6, 2.345343, 3, 5, 5.5, 6, 6.0000001,
    ]);
  });
});
