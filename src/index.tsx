import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package '@developeraspire/react-native-sunmi-barcode-scanner' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const SunmiBarcodeScanner = NativeModules.SunmiBarcodeScanner
  ? NativeModules.SunmiBarcodeScanner
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export default SunmiBarcodeScanner;
