import { ref } from 'vue'

/**
 * Initialize a new multiplier.
 * The initial value is 3.
 * 
 * @returns the multiplier and the increment function
 */
export const newMultiplier = () => {
    const multiplier = ref(3)
    const increment = () => {
        multiplier.value++
    }

    return { multiplier, increment }
}
