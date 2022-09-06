import React, { useState } from 'react';
import { Button, Image, NativeModules, SafeAreaView, StyleSheet, Text } from 'react-native';

const { CalendarModule, CameraModule, Counter, ImagePicker, ShareContent } = NativeModules;

const App = () => {
  const [image, setImage] = useState('');
  // const handlePress = () => {
  //   DemoHello.sayHello('', (err, message) => {
  //     if (err) console.log(err);

  //     console.log(message);
  //   });

  //   DemoHello.formatCurrency(255600, (err, currency) => {
  //     if (err) console.log(err);

  //     console.log(currency);
  //   });
  // };

  // const handlePressCamera = async () => {
  //   ImagePicker.launchCamera((imageUri) => {
  //     setImage(imageUri);
  //   });
  // };

  const handlePressLibary = () => {
    ImagePicker.launchLibrary((picturePath) => {
      setImage(picturePath);
    });
  };

  // const onPressCamera = () => {
  //   Counter.increment((value) => {
  //     console.log(value);
  //   });
  // };

  // const onPressDecrement = () => {
  //   Counter.decrement()
  //     .then((result) => {
  //       setImage(result);
  //     })
  //     .catch((e) => console.log(e.message, e.code));
  // };

  // const onPress = () => {
  //   CalendarModule.createCalendarEvent('testName', 'testLocation', (eventId) => {
  //     console.log(eventId);
  //   });
  // };

  // const onPressOpenCamera = () => {
  //   ImagePicker.launchCamera((result) => {
  //     setImage(result);
  //   });
  // .then((result) => {
  //   setImage(result);
  // })
  // .catch((e) => console.log(e.message, e.code));
  // };

  return (
    <SafeAreaView>
      {/* <Text onPress={handlePress}>sasasasasas121212as</Text> */}

      {/* <Text>asasasoaosaos</Text> */}

      {/* <Text onPress={handlePressCamera}>Open Camera</Text> */}

      <Text onPress={handlePressLibary}>Open Image Libary</Text>

      {/* <Text onPress={onPress}>CalendarModule</Text> */}

      {/* <Text onPress={onPressCamera}>Camera</Text> */}

      {/* <Text onPress={onPressDecrement}>Decrement</Text> */}

      {/* <Text onPress={onPressOpenCamera}>onPressOpenCamera</Text> */}

      <Button
        title='Share'
        onPress={() => ShareContent.share({ message: 'helo with Swift Dev.to Tutorial' })}
      />
      {image && (
        <Image
          style={styles.logo}
          source={{
            uri: `data:image/png;base64,${image}`,
          }}
        />
      )}
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    paddingTop: 50,
  },
  tinyLogo: {
    width: 50,
    height: 50,
  },
  logo: {
    width: 300,
    height: 300,
  },
});

export default App;
