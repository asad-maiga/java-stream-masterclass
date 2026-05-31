package dev.asadmaiga;

import java.util.*;
import java.util.stream.Collectors;

class Transfer {
    private final String id;
    private final String status;
    private final int amount;

    public Transfer(String id, String status, int amount) {
        this.id = id;
        this.status = status;
        this.amount = amount;
    }

    public String getId() { return id; }
    public String getStatus() { return status; }
    public int getAmount() { return amount; }

    @Override
    public String toString() {
        return "Transfer{id='" + id + "', status='" + status + "', amount=" + amount + "}";
    }

    /**
     * ENTERPRISE BEST PRACTICE: Business logic encapsulation inside the entity.
     * This makes the predicate highly reusable across different streams.
     */
    public boolean isHighValueTransaction() {
        return this.amount > 1000 && "SUCCESS".equals(this.status);
    }
}

/**
 * DTO (Data Transfer Object) Pattern:
 * Used to transfer only required and formatted data to upper layers (UI/API),
 * hiding internal domain models for security and decoupling.
 */
class TransferDTO {
    private final String transferId;
    private final String displayAmount;

    public TransferDTO(Transfer transfer) {
        this.transferId = transfer.getId();
        this.displayAmount = transfer.getAmount() + " TRY";
    }

    @Override
    public String toString() {
        return transferId + " - " + displayAmount;
    }
}

public class StreamPlayground {
    public static void main(String[] args) {

        // Mock Data Source acting as an in-memory database store
        List<Transfer> transfers = new ArrayList<>();
        transfers.add(new Transfer("TRX01", "SUCCESS", 500));
        transfers.add(new Transfer("TRX02", "FAILED", 1500));
        transfers.add(new Transfer("TRX03", "SUCCESS", 2500));
        transfers.add(new Transfer("TRX04", "SUCCESS", 100));
        transfers.add(new Transfer("TRX05", "FAILED", 3000));

        System.out.println("=====================================================================");
        System.out.println("MODULE 1: FILTERING OPERATIONS (Predicates)");
        System.out.println("=====================================================================");

        /*
         * BUSINESS USE CASE: Fetch high-value successful transactions.
         * ENTERPRISE BEST PRACTICE: Use encapsulated method references for domain rules.
         */
        List<Transfer> bigSuccessTransfersModern = transfers.stream()
                .filter(Transfer::isHighValueTransaction) // Clean Code: Method Reference
                /*
                 * ALTERNATIVE APPROACH (Inline Lambda Expression):
                 * .filter(t -> t.getStatus().equals("SUCCESS") && t.getAmount() > 1000)
                 */
                .toList(); // Java 16+ Unmodifiable List
        /*
         * ALTERNATIVE APPROACH (Pre-Java 16 Modifiable List):
         * .collect(Collectors.toList())
         */
        System.out.println("Filtered Transactions: " + bigSuccessTransfersModern);


        System.out.println("\n=====================================================================");
        System.out.println("MODULE 2: MAPPING OPERATIONS (Transformations & DTO Pattern)");
        System.out.println("=====================================================================");

        /*
         * BUSINESS USE CASE: Transform Domain Entities to secure Data Transfer Objects (DTOs).
         */
        List<TransferDTO> apiResponseList = transfers.stream()
                .filter(t -> "SUCCESS".equals(t.getStatus()))
                .map(TransferDTO::new) // Constructor Reference
                /*
                 * ALTERNATIVE APPROACH (Standard Lambda Transformation):
                 * .map(t -> new TransferDTO(t))
                 */
                .toList();
        System.out.println("Mapped DTOs for API Response: " + apiResponseList);


        System.out.println("\n=====================================================================");
        System.out.println("MODULE 3: REDUCING OPERATIONS (Aggregations)");
        System.out.println("=====================================================================");

        /*
         * BUSINESS USE CASE: Calculate the financial turnover of all successful transactions.
         */
        int totalSuccessVolume = transfers.stream()
                .filter(t -> "SUCCESS".equals(t.getStatus()))
                .map(Transfer::getAmount) // Extract primitive value stream
                .reduce(0, Integer::sum); // Terminal reduction operation
        /*
         * ALTERNATIVE APPROACH (Standard Lambda Accumulator):
         * .reduce(0, (accumulator, current) -> accumulator + current)
         */
        System.out.println("Total Financial Success Volume: " + totalSuccessVolume + " TRY");


        System.out.println("\n=====================================================================");
        System.out.println("MODULE 4: ADVANCED COLLECTORS (Grouping & Segregation)");
        System.out.println("=====================================================================");

        /*
         * BUSINESS USE CASE: Group financial records by transaction status for reconciliation reports.
         */
        Map<String, List<Transfer>> groupedTransfers = transfers.stream()
                .collect(Collectors.groupingBy(Transfer::getStatus));

        System.out.println("Reconciliation Report - SUCCESSFUL: " + groupedTransfers.get("SUCCESS"));
        System.out.println("Reconciliation Report - FAILED: "     + groupedTransfers.get("FAILED"));


        System.out.println("\n=====================================================================");
        System.out.println("MODULE 5: ADVANCED PIPELINE CONTROL (Sorting, Pagination, Matchers)");
        System.out.println("=====================================================================");

        /*
         * BUSINESS USE CASE: Pagination & Top-N Analysis.
         * Sort all transactions descending by amount, skip the first 1 (simulate page offset), and limit the next 2.
         */
        List<Transfer> prioritizedPagedTransfers = transfers.stream()
                .sorted(Comparator.comparingInt(Transfer::getAmount).reversed()) // Intermediate: Sort Descending
                .skip(1)  // Intermediate: Skip the highest transaction
                .limit(2) // Intermediate: Take the next 2 records
                .toList(); // Terminal Operation
        System.out.println("Paged & Sorted Sub-List (Page 2): " + prioritizedPagedTransfers);

        /*
         * BUSINESS USE CASE: Fraud & Risk Management.
         * Short-circuiting evaluation to check if any transaction violates critical risk thresholds.
         */
        boolean hasHighRiskTransaction = transfers.stream()
                .anyMatch(t -> t.getAmount() >= 3000); // Terminal short-circuit operation
        /*
         * UNDER THE HOOD: Stops processing instantly when the first match is found (Optimized performance).
         */
        System.out.println("Risk Assessment - Has High Risk Activity? " + hasHighRiskTransaction);
        System.out.println("=====================================================================");
    }
}