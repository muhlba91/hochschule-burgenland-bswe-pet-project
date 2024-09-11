import { ref } from 'vue'

/**
 * Initialize a new dividend.
 * The initial value is 15.
 * 
 * @returns the dividend and the decrement function
 */
export const newDividend = () => {
    const dividend = ref(15)
    const decrement = () => {
        dividend.value--
    }

    return { dividend, decrement }
}
