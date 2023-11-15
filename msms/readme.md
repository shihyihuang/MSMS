## Monash Sports Management System (MSMS) User Manual

### **Introduction**

Welcome to the user manual for the Monash Sports Management System (MSMS). Commissioned by Monash Local Government, this system allows for effective member management and appointment handling, shifting away from 3rd party providers. Let's get started!

---

### **Table of Contents**

- [Download & Installation](#download-&-installation)
- [Starting the Program](#starting-the-program)
- [Program Features](#program-features)
  - [Login](#login)
  - [Main Menu](#main-menu)
    - [Make an Appointment](#make-an-appointment)
    - [My Favourite List](#my-favourite-list)
    - [My Booking Details](#my-booking-details)
- [Troubleshooting & Support](#troubleshooting-&-support)
- [Frequently Asked Questions (FAQ)](#Frequently Asked Questions)

---

### **1. Download & Installation** <a name="download-&-installation"></a>

**From Monash GitLab**:

1. **Clone from GitLab**:
   ![gitlab_clone.png](img%2Fgitlab_clone.png)
   `   git clone https://git.infotech.monash.edu/fit5136/fit5136-s2-2023/FIT5136_Team_13.git`

2. **ZIP Download**: If you can't clone the repository, ask a member of the organization to provide you with a ZIP file of the project. Once you have it, extract its contents to a directory of your choice.

---

### **2. Starting the Program** <a name="starting-the-program"></a>

**Using Terminal**:

1. Ensure you have Java installed. You can check by running:
   ```
   java -version
   ```
   If you don't have Java installed, you can download it from here:
   https://www.oracle.com/java/technologies/javase/jdk18-archive-downloads.html
2. Navigate to the directory where you have the project.
3. Compile the Java files:
   ```
   javac *.java
   ```
4. Run the compiled Java program:
   ```
   java main
   ```

**Using IntelliJ IDEA (Recommended)**:

1. Open IntelliJ IDEA.
   - If you don't have IntelliJ IDEA installed, you can download it from here:
     https://www.jetbrains.com/idea/download
2. Go to `File` -> `Open` and navigate to the directory of the project.
   ![IJ_openfile.png](img%2FIJ_openfile.png)
3. Click `OK` to open the project.
4. Once loaded, go to the `Run` menu and select `Run`. Choose the main class to execute.

**Login Details**:
For the demo, use the following credentials:

```
Email: member@student.monash.edu
Password: Monash1234
```

---

### **3. Program Features** <a name="program-features"></a>

#### **3.1. Login**: <a name="login"></a>

Start the program, and you'll be presented with the login screen.

**Step 1**: Press the Enter key to continue.

![welcome.png](img%2Fwelcome.png)

**Step 2**: Input the given demo email and password. For our example Follow the prompts to enter the demo email and password provided above.

![login.png](img%2Flogin.png)

#### **3.2. Main Menu**: <a name="main-menu"></a>

Upon successful login, the main menu will be displayed with several options.

The main menu offers three options:
![main1.png](img%2Fmain1.png)

##### **3.2.1. Make an Appointment**: <a name="make-an-appointment"></a>

**Step 3**: To start booking an appointment, select the first option from the main menu. This can typically be done by typing 1 followed by pressing Enter.

**Step 4**: The system will prompt you with two methods for branch selection.

![findb1.png](img%2Ffindb1.png)

Choose your preferred method.

**Step 5**: If you opted to find by Suburb, you'll be presented with a list. Each Suburb will have a corresponding number.
Input the number of your desired Suburb and press Enter.

![findbselect.png](img%2Ffindbselect.png)

**Step 6**: The system will show detailed information about the chosen branch.
This includes branch name, address, and opening hours.

![bdetail.png](img%2Fbdetail.png)

**Step 6.1**: Add the current branch to your Branch Favorite list by inputting 'f', then press enter.

![branchFav1.png](img%2FbranchFav1.png)

**Step 6.2**: The success message would show up, press enter to proceed.

![branchFav2.png](img%2FbranchFav2.png)

**Step 7**: Switch through the page of PTs and select your desired PT by inputting 'y' in current page, then press Enter.

![ptdetail.png](img%2Fptdetail.png)

**Step 7.1**: Add the current Pt to your Pt Favorite list by inputting 'f', then press enter.
![ptFav1.png](img%2FptFav1.png)

**Step 7.2**: The success message would show up, press enter to proceed.

![ptFav2.png](img%2FptFav2.png)

**Step 8**: You'll be asked about your membership status (e.g., new member, existing member) and the reason for the appointment. Follow the prompts to input this information.

![status&reason.png](img%2Fstatus%26reason.png)

**Step 9**: Next, you'll be prompted to input the desired date for your appointment. Follow the format guidelines provided (DD/MM/YYYY) and press Enter.

![date.png](img%2Fdate.png)

**Step 10**: The system will then show available time slots for the chosen date. Select your desired time by inputting the corresponding number and press Enter.

![time.png](img%2Ftime.png)

**Step 11**: After confirming the date and time, you'll be shown a detailed view of your booking — this includes PT details, date, time, branch, and the reason for the appointment.

![bconfirm.png](img%2Fbconfirm.png)

**Step 12**: If everything looks correct, proceed with the confirmation. The system will provide a success message indicating your appointment has been booked.

![successmsg.png](img%2Fsuccessmsg.png)

##### **3.2.2. My Favourite List**: <a name="my-favourite-list"></a>

Shows your favourite PTs and branches.

**Steps 1**: Select the second option from the main menu. This can typically be done by typing 2 followed by pressing Enter.

![main2.png](img%2Fmain2.png)

**Step 2**: The system will provide you 2 options to choose from. Select the one you want to view by typing the corresponding number and pressing Enter.

![fav1.png](img%2Ffav1.png)

**Step 3**: The system will show you a list of your favourite PTs or branches. Each item will have a corresponding number. Input the number of your desired item and press Enter.

![fav3.png](img%2Ffav3.png)

**Step 4**: The system will show detailed information about the chosen PT or branch.

![fav4.png](img%2Ffav4.png)

##### **3.2.3. My Booking Details**: <a name="my-booking-details"></a>

Review, check-in, or cancel your bookings.

**Step 1**: Select the third option from the main menu. This can typically be done by typing 3 followed by pressing Enter.

![main3.png](img%2Fmain3.png)

**Step 2**: The system will show your bookings page by page. Enter N to go to the next page or P to go to the previous page.

Input 'y' in current page to select the booking you want to view, then press Enter.

![bo1.png](img%2Fbo1.png)

**Step 3**: The system will show the details and the options that about the chosen booking.
The displayed options depend on the current time and your scheduled booking time.

**Step 3.1**: The "check-in" option will be displayed starting from 10 minutes before the scheduled booking time.

Input 'y' and press enter to proceed check-in.
![checkin2.png](img%2Fcheckin2.png)

**Step 3.1.2**: The success message would show up and display how many people are in front of the queue.

Press enter to go to main page.
![checkin3.png](img%2Fcheckin3.png)

**Step 3.2**: The "cancel" option will only be displayed more than 24 hours before the scheduled time.

Input 'cancel' and press enter to cancel the booking.
![cancel2.png](img%2Fcancel2.png)

**Step 3.2.2**: The success message would show up. Press enter to go to main page.
![cancel3.png](img%2Fcancel3.png)

**Step 3.3**: The "cancel" option will not be displayed within 24 hours of the scheduled booking time.
Also, the "check-in" option will only be displayed starting from 10 minutes before the scheduled booking time.

Input '0' to go back to view another booking, or 'm' to go back to main page.
![bo2.png](img%2Fbo2.png)

Note: You can not cancel a booking within 24 hours of the scheduled booking time. You can only check-in a booking if the time of the booking in 10 minutes.

---

### **4. Troubleshooting & Support** <a name="troubleshooting-&-support"></a>

- Ensure Java is properly installed.
- Ensure all commands are typed correctly.
- Restart the program.
- If issues persist, contact the project team or seek support at `syuu0051@student.monash.edu`.

---

### Frequently Asked Questions <a name="frequently-asked-questions"></a>

**Q1:** I forgot my password. How can I reset it?

**A1:** Currently, the system doesn't support self-service password resets. Please contact the support team at `syuu0051@student.monash.edu` for assistance.

**Q2:** The system won't start. What should I do?

**A2:** Ensure that Java is correctly installed and that you're using the recommended version. If issues persist, reach out to the email mentioned above.

**Q3:** Can I book multiple appointments at the same time?

**A3:** No, the system only allows one appointment per time slot. You'll need to book different slots for multiple appointments.

**Q4:** How can I add a PT or branch to my favorites?

**A4:** When viewing a PT or branch detail, there's an option to add it to your favorites. Follow the on-screen instructions to do so.

**Q5:** I made a mistake in my booking. Can I edit it?

**A5:** You cannot directly edit a booking. However, you can cancel the mistaken booking and then create a new one with the correct details.

---

**Thank you for using the MSMS!**
