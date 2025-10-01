package net.trinketina.frogpetting;

import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

public interface IPettingSound {
    default SoundEvent frog_Petting$getPettingSound(String entity_id) {
        if (PettingData.OFFSETS.containsKey(entity_id)) {
            String sound_event_id = PettingData.OFFSETS.get(entity_id).sound_event_id;
            if (sound_event_id != null) {
                return SoundEvent.of(Identifier.of(sound_event_id));
            }
        }
        return null;
    }
    default SoundEvent frog_Petting$getPettingAmbientSound() {
        return null;
    }
    default float frog_Petting$getPettingSoundVolume() {
        return 1.0f;
    }
    default float frog_Petting$getPettingSoundPitch(Random random) {
        return (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F;
        //return  (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
    }
}
