import { initializeApp } from "firebase/app";
import { getMessaging, getToken, onMessage } from "firebase/messaging";

const config = {
  apiKey: import.meta.env.FIREBASE_API_KEY,
  authDomain: import.meta.env.FIREBASE_AUTH_DOMAIN,
  projectId: import.meta.env.FIREBASE_PROJECT_ID,
  storageBucket: import.meta.env.FIREBASE_STORAGE_BUCKET,
  messagingSenderId: import.meta.env.FIREBASE_MESSAGING_SENDER_ID,
  appId: import.meta.env.FIREBASE_APP_ID,
  measurementId: import.meta.env.FIREBASE_MEASUREMENT_ID,
};

const app = initializeApp(config);
const messaging = getMessaging();

getToken(messaging, {
  vapidKey: import.meta.env.FIREBASE_VAPID_KEY,
})
  .then((currentToken) => {
    if (currentToken) {
      localStorage.setItem(fcmToken, currentToken);
    } else {
      console.log("No registration token available. Request permission to generate one.");
    }
  })
  .catch((err) => {
    console.log("An error occurred while retrieving token. ", err);
  });

onMessage(messaging, (payload) => {
  console.log("Message received. ", payload);
});
