import { getToken, onMessage, getMessaging } from "firebase/messaging";
import { initializeApp } from "firebase/app";

const config = {
  apiKey: import.meta.env.VITE_FIREBASE_API_KEY,
  authDomain: import.meta.env.VITE_FIREBASE_AUTH_DOMAIN,
  projectId: import.meta.env.VITE_FIREBASE_PROJECT_ID,
  storageBucket: import.meta.env.VITE_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: import.meta.env.VITE_FIREBASE_MESSAGING_SENDER_ID,
  appId: import.meta.env.VITE_FIREBASE_APP_ID,
  measurementId: import.meta.env.VITE_FIREBASE_MEASUREMENT_ID,
};

const app = initializeApp(config);
const messaging = getMessaging(app);

getToken(messaging, {
  vapidKey: import.meta.env.VITE_FIREBASE_VAPID_KEY,
})
  .then((currentToken) => {
    if (currentToken) {
      localStorage.setItem("FCM-key", currentToken);
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
