package com.example.promojio.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.promojio.R;
import com.example.promojio.controller.PromoService;
import com.example.promojio.controller.UserService;

import org.json.JSONException;

import java.util.Random;

public class SpinWheel extends Fragment {

    private Button spinButton;
    private ImageView wheelImageView;

    private final Random random;
    private int lastAngle;

    private final static int COINS = 50;
    private final static String REWARD_TRY_AGAIN = "Please try again";
    private final static String REWARD_MYSTERY_GIFT = "You won a mystery gift!";
    private final static String REWARD_COINS = "You won " + COINS + " coins!";
    private final static String REWARD_PROMO_CODE = "You won a free promo code!";

    private final static String LOG_TAG = "LOGCAT_SpinWheel";

    public SpinWheel() {
        this.random = new Random();
        this.lastAngle = 0;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.wheel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wheelImageView = view.findViewById(R.id.wheelImageView);
        spinButton = view.findViewById(R.id.spinButton);
        spinButton.setOnClickListener(v -> spinWheel());
    }

    private void spinWheel() {
        // Disable the button before spinning
        spinButton.setEnabled(false);

        // Calculate the amount to spin the wheel by
        int angle = 360*12 + (random.nextInt(360) + 1); // Ensures at least 4 rotations, plus a random amount.
        Log.d(LOG_TAG, "angle: " + angle);
        long lastSpinDuration = 3000 + random.nextInt(2000); // Duration between 3 and 5 seconds

        // Animate the wheel spinning
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                wheelImageView,
                "rotation",
                lastAngle,
                angle + lastAngle
        );
        animator.setDuration(lastSpinDuration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // This is called when the animation ends
                lastAngle += angle;
                lastAngle = lastAngle % 360; // Keep the angle within 0-359 degrees
                Log.d(LOG_TAG, "Final angle: " + lastAngle);
                showResultPopup();
            }
        });
        animator.start();
    }

    private void showResultPopup() {
        // The wheel has been spun and we have the lastAngle recorded

        // Determine the prize based on the normalized angle.
        String prize;
        if ((lastAngle >= 343) || (lastAngle < 19)) {
            prize = REWARD_MYSTERY_GIFT;
        } else if (lastAngle < 55) {
            prize = REWARD_PROMO_CODE;
        } else if (lastAngle < 91) {
            prize = REWARD_TRY_AGAIN;
        } else if (lastAngle < 127) {
            prize = REWARD_COINS;
        } else if (lastAngle < 163) {
            prize = REWARD_MYSTERY_GIFT;
        } else if (lastAngle < 199) {
            prize = REWARD_TRY_AGAIN;
        } else if (lastAngle < 235) {
            prize = REWARD_PROMO_CODE;
        } else if (lastAngle < 271) {
            prize = REWARD_MYSTERY_GIFT;
        } else if (lastAngle < 307) {
            prize = REWARD_TRY_AGAIN;
        } else { // normalizedAngle < 343
            prize = REWARD_COINS;
        }

        // Perform backend updates
        MainActivity mainActivity = (MainActivity) requireActivity();
        switch (prize) {
            case REWARD_COINS:
                UserService.newInstance().updateUserPoints(
                        getContext(),
                        response -> {},
                        COINS
                );
                mainActivity.notifyTab(R.id.mHome);
                break;
            case REWARD_PROMO_CODE:
                PromoService.getRandomPromo(
                        getContext(),
                        response -> {
                            try {
                                UserService.newInstance().addPromoToUser(
                                        getContext(),
                                        response1 -> {},
                                        response.getJSONObject("promo").getString("id")
                                );
                            }
                            catch (JSONException e) {
                                Log.e(LOG_TAG, "Unable to obtain promo from response: " + e);
                            }
                        }
                );
                mainActivity.notifyTab(R.id.mPromos);
                break;
            case REWARD_MYSTERY_GIFT:
                UserService.newInstance().updateUserTierPoints(
                        getContext(),
                        response -> {},
                        Math.abs(this.random.nextInt() % 1000)
                );
                mainActivity.notifyTab(R.id.mHome);
                break;
        }

        // Notify user via AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        if (prize.equals(REWARD_TRY_AGAIN)) {
            builder.setTitle("Sorry :(");
        }
        else {
            builder.setTitle("Congratulations!");
        }
        builder.setMessage(prize);
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();

        // Re-enable the spin button
        spinButton.setEnabled(true);
    }
}
