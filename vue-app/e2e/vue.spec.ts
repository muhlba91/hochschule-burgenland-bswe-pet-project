import { test, expect } from '@playwright/test';

test('visits the app root url', async ({ page }) => {
  await page.goto('/');
  await expect(page.locator('div.greetings > h1')).toHaveText('You did it!');
})

test('increments the multiplier', async ({ page }) => {
  await page.goto('/');
  await expect(page.locator('p#multiplication')).toHaveText('The result of your multiplication is: 5 * 3 = 15');
  await page.locator('#increment').click();
  await expect(page.locator('p#multiplication')).toHaveText('The result of your multiplication is: 5 * 4 = 20');
})

test('decrements the dividend', async ({ page }) => {
  await page.goto('/');
  await expect(page.locator('p#division')).toHaveText('The result of your division is: 15 / 5 = 3');
  await page.locator('#decrement').click();
  await expect(page.locator('p#division')).toHaveText('The result of your division is: 14 / 5 = 2.8');
})
