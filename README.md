
### Testing with ADB Commands

Testing Command with custom TEST_BOOT_COMPLETED broadcast

```sh
adb shell am broadcast -a com.andproger.testtaskaura.TEST_BOOT_COMPLETED --receiver-include-background
```

If multiple devices are running and you want to target a specific device (e.g., `emulator-5556`), use:
```sh
adb -s emulator-5556 shell am broadcast -a com.andproger.testtaskaura.TEST_BOOT_COMPLETED --receiver-include-background
```


# Test task (Boot counter app)

The aim of this application is to monitor when the device boots up and to set up a repeating task for displaying notifications containing boot event details. Users can dismiss these notifications, which will prompt the app to reschedule them accordingly.

## Boot Event
The application must listen for the `RECEIVE_BOOT_COMPLETED` event. Once the event is triggered, the application must persist such an event with the relevant information.

## Application Behavior
Any time the application is awakened, it must schedule the action to be executed.

### General Information About the Action:
- It must run every 15 minutes (15 min rule).
- On run, it must show the notification with the “special” body.
- Restore the notification with the updated information on the boot event ONLY IF the notification was present before the (re)boot.

### Notification Dismiss Action:
- It should be rescheduled based on the configuration and shouldn’t take into account the 15 minutes rule.

## Notification Body Text
There are three possible texts within the notification body:

1. **No Boot Events:**
   - If no boot events were detected within the app’s lifetime, the body text should be:
     ```
     No boots detected
     ```

2. **Single Boot Event:**
   - If only one boot event was detected, the text must be:
     ```
     The boot was detected = ${date_of_the_boot_event}
     ```
      - `date_of_the_boot_event` is formatted as `DD/MM/YYYY HH:MM:SS`

3. **Multiple Boot Events:**
   - If multiple events were detected, the text should be:
     ```
     Last boots time delta = ${time_between_2_last_boot_events}
     ```
      - `time_between_2_last_boot_events` is the delta between the last and pre-last boot events.

## Notification Dismiss Action
Upon dismissal, the notification should be rescheduled to reappear according to the following setup:
- **Total Dismissals Allowed:** 5
- **Interval Between Dismissals:** Dismissal count * 20 minutes
- **Notification Content:** Must adhere to the "Special" body requirements.

If the total number of dismissals exceeds the limit, notification scheduling should revert to the 15-minute rule. For example, if the user dismisses the notification 5 times, the next notification should appear in 15 minutes, and further dismissals should trigger the behavior outlined above.

## User Interface (UI)
Any `View` component can be used to display the information. For example, it can be a simple `TextView`.

### UI Text Based on Boot Events:
- **No Boot Events:**
   - If no boot events were triggered within the app’s lifetime, then the text must be:
     ```
     No boots detected
     ```

- **Boot Events Present:**
   - If there were boot events during the app’s lifetime, the `TextView` must be populated with the info of each boot event:
      - Date of the boot events (e.g. `01/04/2024`)
      - Number of boot events per day (e.g. `5, 2, 3`)

### UI Example Text:
```
01/04/2024 - 5
02/04/2024 - 10
06/04/2024 - 3
```

### Configurable UI Components:
The UI should have the ability to change the “Total dismissals allowed” and “Interval between dismissals” using UI components (can be simple edit texts).

### Notes
Consider adding `--receiver-include-background` if it’s planned to verify “boot events” via ADB.
