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
  apiKey: "AIzaSyCQlMwrOrejMrA-qb_NtEdeA_z51qUnKIg",
  authDomain: "feedbackapp-f253f.firebaseapp.com",
  projectId: "feedbackapp-f253f",
  storageBucket: "feedbackapp-f253f.appspot.com",
  messagingSenderId: "444228604992",
  appId: "1:444228604992:web:8ca15b9e9e48ccbbbabba5",
  measurementId: "G-44SVGMSQXR"
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
