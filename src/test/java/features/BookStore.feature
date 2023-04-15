Feature: BookStore
  Scenario: Generate user
    * Create a user
    * Generate token for the user in context

  Scenario: Create user
    * Generate token for following user:
      | Username | Pickleboy1      |
      | Password | s3curePassw0rd! |

  @Authorise
  Scenario: Post book
    * Get all book from database
    * Post books by publisher named O'Reilly Media to the user in context
    * Get user in context
    * Verify book information for the user in context
