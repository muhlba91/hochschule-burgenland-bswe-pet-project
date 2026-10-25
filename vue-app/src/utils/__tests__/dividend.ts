import { describe, it, expect } from 'vitest'

import { newDividend } from '../dividend'

describe('dividend', () => {
  it('initializes properly', () => {
    const { dividend, decrement } = newDividend()
    expect(dividend.value).toBe(15)

    decrement()
    expect(dividend.value).toBe(14)
  })
})
