# RSA Cryptosystem

RSA Cryptosystem is a JavaFX application for displaying RSA encryption and decryption algorithms.


## Usage
*All steps need to be done in order for appropriate results*!

### Start
To make use of the application clone/install the repository and open the **out/artifacts/pa3_jar** folder.
Inside you will find **pa3.jar**, run this.
### Encryption
**Step 1: Calculating p and q**

1. Fill in a number for: n
2. Press on **Step 1** for the result of p and q.

 *If nothing happens take another value for n. This could happen because p and q both need to be prime numbers.*

**Step 2: Calculating e**

1. Press on **Step 2** to calculate: e

**Step 3: Encrypting the message**

1. Fill in your desired text to be encrypted into the text area below ***Encryption Message***
2. Press on **Step 3** to encrypt your message

### Decryption
**Step 1: Calculating d**
1. Fill in a number for: n
2. Fill in a number for: e
3. Press on **Step 1** for the result of d

**Step 2: Decrypting the message**

1. Fill in your desired cipher text to be decrypted into the text area below ***Message for decryption***
2. Press on **Step 2** to decrypt your message

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.


## Exceptions
**Exception #1**
If, during the encryption phase, you enter for example n = 256, you get a p and q that are both two. This is obviously not true, but the reason we get these numbers is that 2  ×  2  ×  2  ×  2  ×  2  ×  2  ×  2  ×  2 = 256, those are the prime factors for the value of n. This means that these values are not really incorrect, but actually a small piece of a larger picture.

**Exception #2**
Another error could occur when the user uses a prime number for n on the Encryption screen during Step 1. This will be an incorrect value since factoring 2 primes (p and q) cannot result into a prime number, but in a semiprime. If this error occurs, there will be no p or q displayed.
