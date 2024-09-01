# @developeraspire/react-native-sunmi-barcode-scanner

A React native intergation of the Sunmi device built-in barcode scanner, for android only.

## Installation

Using Yarn

```sh
yarn add @developeraspire/react-native-sunmi-barcode-scanner
```

Using Npm

```sh
npm install @developeraspire/react-native-sunmi-barcode-scanner
```

## Usage

```js
import SunmiBarcodeScanner from '@developeraspire/react-native-sunmi-barcode-scanner';

const scannedCode = await SunmiBarcodeScanner.startScanner();

console.log("Scanned Code:" scannedCode)

```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Created by [Franklin Okolie](https://github.com/DeveloperAspire)

<!-- Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob) -->
