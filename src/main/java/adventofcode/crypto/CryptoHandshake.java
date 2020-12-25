/*
 * Copyright (c) 2020 James Carman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package adventofcode.crypto;

import java.util.stream.Stream;

import com.codepoetics.protonpack.StreamUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CryptoHandshake {

//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    public static final int HANDSHAKE_SUBJECT_NUMBER = 7;

//----------------------------------------------------------------------------------------------------------------------
// Static Methods
//----------------------------------------------------------------------------------------------------------------------

    public static long performHandshake(long cardPublicKey, long doorPublicKey) {
        return performTransforms(findLoopSize(cardPublicKey), doorPublicKey);
    }

    private static long performTransforms(long loopSize, long subjectNumber) {
        return Stream.generate(new CryptoTransformGenerator(subjectNumber))
                .skip(loopSize - 1)
                .findFirst()
                .orElse(-1L);
    }

    public static long findLoopSize(long publicKey) {
        return StreamUtils.zipWithIndex(Stream.generate(new CryptoTransformGenerator(HANDSHAKE_SUBJECT_NUMBER)))
                .filter(i -> i.getValue() == publicKey)
                .map(i -> i.getIndex() + 1)
                .findFirst()
                .orElse(-1L);
    }
}
