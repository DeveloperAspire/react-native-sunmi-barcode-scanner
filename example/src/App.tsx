import { useState } from 'react';
import { StyleSheet, View, Text, Button } from 'react-native';
import SunmiBarcodeScanner from 'react-native-sunmi-barcode-scanner';

export default function App() {
  const [result, setResult] = useState<number | undefined>();

  const handleStartScanner = async () => {
    const scannedResult = await SunmiBarcodeScanner.startScanner();

    setResult(scannedResult);
  };

  return (
    <View style={styles.container}>
      <Button title="Start Scanner" onPress={handleStartScanner} />

      <Text>SCANNED RESULT: {result}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
