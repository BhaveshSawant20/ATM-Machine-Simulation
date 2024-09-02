import time
import hashlib

# User database with hashed PINs
user_database = {
    "default_user": {
        "pin": hashlib.sha256(b"1234").hexdigest(),
        "balance": 10000,
        "transactions": []
    }
}

def authenticate_user(pin):
    """Authenticate user based on PIN."""
    hashed_pin = hashlib.sha256(pin.encode()).hexdigest()
    if user_database["default_user"]["pin"] == hashed_pin:
        return True
    return False

def atm_machine():
    print("Please insert Your CARD")
    
    # Simulate card processing
    time.sleep(3)
    
    # Taking ATM pin from the user
    pin = input("Enter your ATM pin: ")
    
    # Authenticate user
    if not authenticate_user(pin):
        print("Authentication failed. Wrong PIN.")
        return
    
    # User account details
    user = user_database["default_user"]
    balance = user["balance"]
    transactions = user["transactions"]
    
    # Loop will run until the user exits
    while True:
        # Showing options to the user
        print("""
        1 == Balance
        2 == Withdraw balance
        3 == Deposit balance
        4 == Transaction History
        5 == Exit
        """)
        
        try:
            # Taking an option from the user
            option = int(input("Please enter your choice: "))
        except ValueError:
            print("Please enter a valid option.")
            continue
        
        if option == 1:
            print(f"Your current balance is {balance}")
            
        elif option == 2:
            try:
                withdraw_amount = int(input("Please enter withdraw amount: "))
            except ValueError:
                print("Invalid input. Please enter numeric value.")
                continue
            
            if withdraw_amount > balance:
                print("Insufficient balance.")
            else:
                balance -= withdraw_amount
                transactions.append(f"Withdrawal of {withdraw_amount} units")
                print(f"{withdraw_amount} is debited from your account.")
                print(f"Your updated balance is {balance}")
                
        elif option == 3:
            try:
                deposit_amount = int(input("Please enter deposit amount: "))
            except ValueError:
                print("Invalid input. Please enter numeric value.")
                continue
            
            balance += deposit_amount
            transactions.append(f"Deposit of {deposit_amount} units")
            print(f"{deposit_amount} is credited to your account.")
            print(f"Your updated balance is {balance}")
            
        elif option == 4:
            print("Transaction History:")
            if not transactions:
                print("No transactions yet.")
            else:
                for transaction in transactions:
                    print(transaction)
                
        elif option == 5:
            # Update the user's balance and transactions before exiting
            user["balance"] = balance
            user["transactions"] = transactions
            print("Thank you for using our ATM. Have a great day!")
            break
            
        else:
            print("Invalid option. Please try again.")

# Run the ATM machine function
if __name__ == "__main__":
    atm_machine()
