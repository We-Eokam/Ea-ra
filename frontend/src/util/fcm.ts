import { getToken, onMessage, getMessaging } from "firebase/messaging";
import { initializeApp } from "firebase/app";
import axiosInstance from "../api/axiosInstance.ts";
import toast from "react-hot-toast";

const config = {
  apiKey: import.meta.env.VITE_FIREBASE_API_KEY,
  authDomain: import.meta.env.VITE_FIREBASE_AUTH_DOMAIN,
  projectId: import.meta.env.VITE_FIREBASE_PROJECT_ID,
  storageBucket: import.meta.env.VITE_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: import.meta.env.VITE_FIREBASE_MESSAGING_SENDER_ID,
  appId: import.meta.env.VITE_FIREBASE_APP_ID,
  measurementId: import.meta.env.VITE_FIREBASE_MEASUREMENT_ID,
};

const axios = axiosInstance();

const app = initializeApp(config);
const messaging = getMessaging(app);

const initFcm = () => {
  void Notification.requestPermission().then((permission) => {
    if (permission === "granted") {
      getToken(messaging, {
        vapidKey: import.meta.env.VITE_FIREBASE_VAPID_KEY,
      })
        .then((currentToken) => {
          if (currentToken) {
            axios
              .post("/notification", {
                token: currentToken,
              })
              .then((data) => {
                console.log(data);
              });
            // alert("set token : " + currentToken);
            // alert("알림 설정 성공 알림")
            toast.success("알림 수신에 동의하였습니다", {
              style: {
                zIndex: "20",
              },
            });
            localStorage.setItem("FCM-key", currentToken);
          } else {
            console.log(
              "No registration token available. Request permission to generate one."
            );
          }
        })
        .catch((err) => {
          console.log("An error occurred while retrieving token. ", err);
        });

      onMessage(messaging, (payload) => {
        console.log("Message received. ", payload);
      });
    } else {
      alert("브라우저 설정에서 알림을 허용해주세요!");
    }
  });
};

export default initFcm;
