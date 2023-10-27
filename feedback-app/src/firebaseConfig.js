import { initializeApp } from "firebase/app";
import { getFirestore, doc, setDoc, getDoc } from 'firebase/firestore';
import { 
  getAuth, 
  createUserWithEmailAndPassword, 
  signInWithEmailAndPassword, 
  signOut, 
  sendEmailVerification, 
  sendPasswordResetEmail,
  updatePassword
} from 'firebase/auth';

const firebaseConfig = {
  apiKey: process.env.REACT_APP_FIREBASE_API_KEY,
  authDomain: process.env.REACT_APP_FIREBASE_AUTH_DOMAIN,
  projectId: process.env.REACT_APP_PROJECT_ID,
  storageBucket: process.env.REACT_APP_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: process.env.REACT_APP_FIREBASE_MESSAGING_SENDER_ID,
  appId: process.env.REACT_APP_FIREBASE_API_ID,
  measurementId: process.env.REACT_APP_FIREBASE_MEASUREMENT_ID,
};
class Firebase {
  constructor() {
    this.firebase = initializeApp(firebaseConfig);

    this.auth = getAuth();
    this.firestore = getFirestore();
  }

  signUp = (email, password) =>
    createUserWithEmailAndPassword(this.auth, email, password);

  signIn = (email, password) =>
    signInWithEmailAndPassword(this.auth, email, password);

  signOut = () => signOut(this.auth);

  sendEmailVerificationLink = () =>
    sendEmailVerification(this.auth.currentUser, {
      url: process.env.REACT_APP_EMAIL_CONFIRMATION_REDIRECT,
    });

  resetPassword = email => sendPasswordResetEmail(this.auth, email);

  updatePassword = password => updatePassword(this.auth.currentUser, password);

  addUser = (uid, data) => setDoc(doc(this.firestore, 'users', uid), data);

  getUser = uid => getDoc(doc(this.firestore, 'users', uid));
}

export default Firebase;
