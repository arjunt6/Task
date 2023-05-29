package com.product;

import java.util.Scanner;

public class Product {
   
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] products = {"Product A", "Product B", "Product C"};
        double[] prices = {20.0, 40.0, 50.0};
        int[] quantities = new int[products.length];
        for (int i = 0; i < products.length; i++) {
            System.out.print("Enter the quantity of " + products[i] + ": ");
            quantities[i] = scanner.nextInt();
        }

        System.out.print("Do you want gift wrap for the products? (Y/N): ");
        String giftWrapInput = scanner.next();
        boolean giftWrap = giftWrapInput.equalsIgnoreCase("Y");

        double[] productTotalAmounts = new double[products.length];
        double subtotal = 0.0;
        for (int i = 0; i < products.length; i++) {
            productTotalAmounts[i] = quantities[i] * prices[i];
            subtotal += productTotalAmounts[i];
            System.out.println(products[i] + " - Quantity: " + quantities[i] + ", Total Amount: $" + productTotalAmounts[i]);
        }

        double flat10DiscountAmount = 10.0;
        double bulk5DiscountPercentage = 0.05;
        double bulk10DiscountPercentage = 0.10;
        double tiered50DiscountPercentage = 0.50;
        int bulk5 = 10;
        int bulk10 = 20;
        int tiered50Quantity= 30;
        int tiered50ProductQuantity = 15;
        double discountAmount = 0.0;
        String discountName = "";

        if (subtotal > 200) {
            discountAmount = flat10DiscountAmount;
            discountName = "flat_10_discount";
        } else if (checkBulk5(quantities, bulk5)) {
            discountAmount = calculateBulk5(productTotalAmounts, bulk5DiscountPercentage);
            discountName = "bulk_5_discount";
        } else if (checkBulk10(quantities, bulk10)) {
            discountAmount = subtotal * bulk10DiscountPercentage;
            discountName = "bulk_10_discount";
        } else if (checkTiered50(quantities, tiered50Quantity, tiered50ProductQuantity)) {
            discountAmount = calculateTiered50(productTotalAmounts, tiered50ProductQuantity, tiered50DiscountPercentage);
            discountName = "tiered_50_discount";
        }

        double giftWrapFeePerUnit = 1.0;
        int itemsPerPackage = 10;
        double shippingFeePerPackage = 5.0;	        
	double giftWrapFee = giftWrap ? getGiftWrapFee(quantities, giftWrapFeePerUnit) : 0.0;
        double shippingFee = getShippingFee(quantities, itemsPerPackage, shippingFeePerPackage);

        double total = subtotal - discountAmount + giftWrapFee + shippingFee;

        System.out.println("Subtotal: $" + subtotal);
        System.out.println("Discount Applied: " + discountName + " - Amount: $" + discountAmount);
        System.out.println("Shipping Fee: $" + shippingFee);
        System.out.println("Gift Wrap Fee: $" + giftWrapFee);
        System.out.println("Total: $" + total);

        scanner.close();
    }

    private static boolean checkBulk5(int[] quantities, int bulk5) {
        for (int quantity : quantities) {
            if (quantity > bulk5) {
                return true;
            }
        }
        return false;
    }

    private static double calculateBulk5(double[] productTotalAmounts, double bulk5DiscountPercentage) {
        double discountAmount = 0.0;
        for (int i = 0; i < productTotalAmounts.length; i++) {
            if (productTotalAmounts[i] > 0 && productTotalAmounts[i] > 10) {
                discountAmount += productTotalAmounts[i] * bulk5DiscountPercentage;
            }
        }
        return discountAmount;
    }

    private static boolean checkBulk10(int[] quantities, int bulk10) {
        int totalQuantity = 0;
        for (int quantity : quantities) {
            totalQuantity += quantity;
        }
        return totalQuantity > bulk10;
    }

    private static boolean checkTiered50(int[] quantities, int tiered50Quantity, int tiered50ProductQuantity) {
        int totalQuantity = 0;
        for (int quantity : quantities) {
            totalQuantity += quantity;
            if (quantity > tiered50ProductQuantity) {
                return true;
            }
        }
        return totalQuantity > tiered50Quantity;
    }

    private static double calculateTiered50(double[] productTotalAmounts, int tiered50ProductQuantity, double tiered50DiscountPercentage) {
        double discountAmount = 0.0;
        for (int i = 0; i < productTotalAmounts.length; i++) {
            if (productTotalAmounts[i] > 0 && productTotalAmounts[i] > tiered50ProductQuantity) {
                double excessQuantity = productTotalAmounts[i] - tiered50ProductQuantity;
                discountAmount += excessQuantity * tiered50DiscountPercentage;
            }
        }
        return discountAmount;
    }

    private static double getGiftWrapFee(int[] quantities, double giftWrapFeePerUnit) {
        double totalGiftWrapFee = 0.0;
        for (int quantity : quantities) {
            totalGiftWrapFee += quantity * giftWrapFeePerUnit;
        }
        return totalGiftWrapFee;
    }

    private static double getShippingFee(int[] quantities, int itemsPerPackage, double shippingFeePerPackage) {
        int totalItems = 0;
        for (int quantity : quantities) {
            totalItems += quantity;
        }
        int totalPackages = (int) Math.ceil((double) totalItems / itemsPerPackage);
        return totalPackages * shippingFeePerPackage;
    }
}
