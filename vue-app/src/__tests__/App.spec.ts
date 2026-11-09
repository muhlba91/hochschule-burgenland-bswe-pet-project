import { describe, it, expect } from 'vitest'

import { mount } from '@vue/test-utils'
import App from '../App.vue'

describe('App', () => {
  it('renders properly', () => {
    const wrapper = mount(App, {})
    expect(wrapper.text()).toContain('The result of your multiplication is: 5 * 3 = 15')
    expect(wrapper.text()).toContain('The result of your division is: 15 / 5 = 3')
  })

  it('increases properly', async () => {
    const wrapper = mount(App, {})
    expect(wrapper.text()).toContain('The result of your multiplication is: 5 * 3 = 15')
    await wrapper.find('#increment').trigger('click')
    expect(wrapper.text()).toContain('The result of your multiplication is: 5 * 4 = 20')
  })

  it('decreases properly', async () => {
    const wrapper = mount(App, {})
    expect(wrapper.text()).toContain('The result of your division is: 15 / 5 = 3')
    await wrapper.find('#decrement').trigger('click')
    expect(wrapper.text()).toContain('The result of your division is: 14 / 5 = 2.8')
  })
})
