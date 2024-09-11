import { describe, it, expect } from 'vitest'

import { mount } from '@vue/test-utils'
import HelloWorld from '../HelloWorld.vue'

describe('HelloWorld', () => {
  it('renders properly', () => {
    const wrapper = mount(HelloWorld, { props: { msg: 'Hello Vitest', multiplier: 3, multiplicand: 5, dividend: 15, divisor: 5 } })
    expect(wrapper.text()).toContain('Hello Vitest')
    expect(wrapper.text()).toContain('The result of your multiplication is: 5 * 3 = 15')
    expect(wrapper.text()).toContain('The result of your division is: 15 / 5 = 3')
  })
})
