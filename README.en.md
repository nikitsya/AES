# AES File Encryptor (Java)

Console app for encrypting and decrypting `.txt` files using **AES-128 (CBC/PKCS5Padding)**.

## Features

- Encrypt text files into `ciphertext.txt`
- Decrypt files back into `plaintext.txt`
- Auto-generate a random 16-byte AES key on encryption
- Key is shown in Base64 for easy copy/paste
- Simple menu-driven CLI

## Workflow

1. Choose `1` to encrypt or `2` to decrypt.
2. Enter a file name (if `.txt` is missing, it is added automatically).
3. Save the generated Base64 key after encryption.
4. Use the same key for decryption.

## Output files

- Encryption result: `ciphertext.txt`
- Decryption result: `plaintext.txt`

## Security note

This is an educational project. The current code uses a fixed zero IV in CBC mode, which is not secure for production use.

